














from __future__ import absolute_import
from __future__ import print_function
from __future__ import division

import importlib
from inspect import getargspec
from six.moves import xrange
import threading
import time
import warnings

from tensorforce import TensorForceError
from tensorforce.execution.base_runner import BaseRunner
from tensorforce.agents.learning_agent import LearningAgent
from tensorforce.agents import agents as AgentsDictionary


class ThreadedRunner(BaseRunner):
    


    def __init__(self, agent, environment, repeat_actions=1, save_path=None, save_episodes=None, save_frequency=None,
                 save_frequency_unit=None, agents=None, environments=None):
        

        if agents is not None:
            warnings.warn("WARNING: `agents` parameter is deprecated, use `agent` instead.",
                          category=DeprecationWarning)
            agent = agents
        if environments is not None:
            warnings.warn("WARNING: `environments` parameter is deprecated, use `environment` instead.",
                          category=DeprecationWarning)
            environment = environments
        super(ThreadedRunner, self).__init__(agent, environment, repeat_actions)

        if len(agent) != len(environment):
            raise TensorForceError("Each agent must have its own environment. Got {a} agents and {e} environments.".
                                   format(a=len(self.agent), e=len(self.environment)))
        self.save_path = save_path
        self.save_episodes = save_episodes
        if self.save_episodes is not None:
            warnings.warn("WARNING: `save_episodes` parameter is deprecated, use `save_frequency` AND "
                          "`save_frequency_unit` instead.",
                          category=DeprecationWarning)
            self.save_frequency = self.save_episodes
            self.save_frequency_unit = "e"
        else:
            self.save_frequency = save_frequency
            self.save_frequency_unit = save_frequency_unit

        
        self.episode_list_lock = threading.Lock()
        
        self.should_stop = False
        
        self.time = None

    def close(self):
        self.agent[0].close()  
        for e in self.environment:
            e.close()

    def run(
        self,
        num_episodes=-1,
        max_episode_timesteps=-1,
        episode_finished=None,
        summary_report=None,
        summary_interval=0,
        num_timesteps=None,
        deterministic=False,
        episodes=None,
        max_timesteps=None,
        testing=False,
        sleep=None
    ):
        


        
        if episodes is not None:
            num_episodes = episodes
            warnings.warn("WARNING: `episodes` parameter is deprecated, use `num_episodes` instead.",
                          category=DeprecationWarning)
        assert isinstance(num_episodes, int)
        
        if max_timesteps is not None:
            max_episode_timesteps = max_timesteps
            warnings.warn("WARNING: `max_timesteps` parameter is deprecated, use `max_episode_timesteps` instead.",
                          category=DeprecationWarning)
        assert isinstance(max_episode_timesteps, int)

        if summary_report is not None:
            warnings.warn("WARNING: `summary_report` parameter is deprecated, use `episode_finished` callback "
                          "instead to generate summaries every n episodes.",
                          category=DeprecationWarning)

        self.reset()

        
        self.global_episode = 0
        self.global_timestep = 0
        self.should_stop = False

        
        threads = [threading.Thread(target=self._run_single, args=(t, self.agent[t], self.environment[t],),
                                    kwargs={"deterministic": deterministic,
                                            "max_episode_timesteps": max_episode_timesteps,
                                            "episode_finished": episode_finished,
                                            "testing": testing,
                                            "sleep": sleep})
                   for t in range(len(self.agent))]

        
        self.start_time = time.time()
        [t.start() for t in threads]

        
        try:
            next_summary = 0
            next_save = 0 if self.save_frequency_unit != "s" else time.time()
            while any([t.is_alive() for t in threads]) and self.global_episode < num_episodes or num_episodes == -1:
                self.time = time.time()

                
                if summary_report is not None and self.global_episode > next_summary:
                    summary_report(self)
                    next_summary += summary_interval

                if self.save_path and self.save_frequency is not None:
                    do_save = True
                    current = None
                    if self.save_frequency_unit == "e" and self.global_episode > next_save:
                        current = self.global_episode
                    elif self.save_frequency_unit == "s" and self.time > next_save:
                        current = self.time
                    elif self.save_frequency_unit == "t" and self.global_timestep > next_save:
                        current = self.global_timestep
                    else:
                        do_save = False

                    if do_save:
                        self.agent[0].save_model(self.save_path)
                        
                        while next_save < current:
                            next_save += self.save_frequency
                time.sleep(1)

        except KeyboardInterrupt:
            print('Keyboard interrupt, sending stop command to threads')

        self.should_stop = True

        
        [t.join() for t in threads]
        print('All threads stopped')

    def _run_single(self, thread_id, agent, environment, deterministic=False,
                    max_episode_timesteps=-1, episode_finished=None, testing=False, sleep=None):
        


        
        old_episode_finished = False
        if episode_finished is not None and len(getargspec(episode_finished).args) == 1:
            old_episode_finished = True

        episode = 0
        
        while not self.should_stop:
            state = environment.reset()
            agent.reset()
            self.global_timestep, self.global_episode = agent.timestep, agent.episode
            episode_reward = 0

            
            time_step = 0
            time_start = time.time()
            while True:
                action, internals, states = agent.act(states=state, deterministic=deterministic, buffered=False)
                reward = 0
                for repeat in xrange(self.repeat_actions):
                    state, terminal, step_reward = environment.execute(action=action)
                    reward += step_reward
                    if terminal:
                        break

                if not testing:
                    
                    
                    agent.atomic_observe(
                        states=state,
                        actions=action,
                        internals=internals,
                        reward=reward,
                        terminal=terminal
                    )

                if sleep is not None:
                    time.sleep(sleep)

                time_step += 1
                episode_reward += reward

                if terminal or time_step == max_episode_timesteps:
                    break

                
                if self.should_stop:
                    return

            self.global_timestep += time_step

            
            self.episode_list_lock.acquire()
            self.episode_rewards.append(episode_reward)
            self.episode_timesteps.append(time_step)
            self.episode_times.append(time.time() - time_start)
            self.episode_list_lock.release()

            if episode_finished is not None:
                
                if old_episode_finished:
                    summary_data = {
                        "thread_id": thread_id,
                        "episode": episode,
                        "timestep": time_step,
                        "episode_reward": episode_reward
                    }
                    if not episode_finished(summary_data):
                        return
                
                elif not episode_finished(self, thread_id):
                    return

            episode += 1

    
    @property
    def agents(self):
        return self.agent

    @property
    def environments(self):
        return self.environment

    @property
    def episode_lengths(self):
        return self.episode_timesteps

    @property
    def global_step(self):
        return self.global_timestep


def WorkerAgentGenerator(agent_class):
    


    
    if isinstance(agent_class, str):
        agent_class = AgentsDictionary.get(agent_class)
        
        if not agent_class and agent_class.find('.') != -1:
            module_name, function_name = agent_class.rsplit('.', 1)
            module = importlib.import_module(module_name)
            agent_class = getattr(module, function_name)

    class WorkerAgent(agent_class):
        


        def __init__(self, model=None, **kwargs):
            
            self.model = model
            
            if not issubclass(agent_class, LearningAgent):
                kwargs.pop("network")
            
            super(WorkerAgent, self).__init__(**kwargs)

        def initialize_model(self):
            
            return self.model

    return WorkerAgent


def clone_worker_agent(agent, factor, environment, network, agent_config):
    

    ret = [agent]
    for i in xrange(factor - 1):
        worker = WorkerAgentGenerator(type(agent))(
            states=environment.states,
            actions=environment.actions,
            network=network,
            model=agent.model,
            **agent_config
        )
        ret.append(worker)

    return ret






















import networkx as nx

from nupic.frameworks.viz import DotRenderer as DEFAULT_RENDERER



class NetworkVisualizer(object):
  


  def __init__(self, network):
    self.network = network


  def export(self):
    

    graph = nx.MultiDiGraph()

    
    regions = self.network.getRegions()

    for idx in xrange(regions.getCount()):
      regionPair = regions.getByIndex(idx)
      regionName = regionPair[0]
      graph.add_node(regionName, label=regionName)

    
    
    for linkName, link in self.network.getLinks():
      graph.add_edge(link.getSrcRegionName(),
                     link.getDestRegionName(),
                     src=link.getSrcOutputName(),
                     dest=link.getDestInputName())

    return graph


  def render(self, renderer=DEFAULT_RENDERER):
    

    renderer().render(self.export())

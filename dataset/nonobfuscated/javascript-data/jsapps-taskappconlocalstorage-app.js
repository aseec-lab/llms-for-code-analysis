
let formTask = document.querySelector('#formTask');
formTask.addEventListener('submit', saveTask);

function saveTask(e) {
	e.preventDefault();

	let title = document.querySelector('#title').value;
	let description = document.querySelector('#description').value;

	if (title == '') {
		inputEmpty();
		return false;
	}

	const task = {
		title,
		description
	}

	if (localStorage.getItem('tasks') === null) {
		let tasks = [];
		tasks.push(task);
		localStorage.setItem('tasks', JSON.stringify(tasks));
	} else {
		let tasks = JSON.parse(localStorage.getItem('tasks'));
		tasks.push(task);
		localStorage.setItem('tasks', JSON.stringify(tasks));
	}
	getTask();
	formTask.reset();
}

function inputEmpty() {
	console.log('empty');
}

function getTask() {
	let tasks = JSON.parse(localStorage.getItem('tasks'));
	let taskView = document.querySelector('#tasks');

	taskView.innerHTML = '';

	for (let i = 0; i < tasks.length; i++) {

		let title = tasks[i].title;
		let description = tasks[i].description;

		listItem = `
		<div class="card my-3">
			<div class="card-body">
				<h4>${title}</h4>
				<p>${description}</p>
				<a class="btn btn-danger" onClick="deleteTask('${title}')">delete</a>
			</div>
		</div>`
		taskView.insertAdjacentHTML('afterbegin', listItem);
	}
}

function deleteTask(title) {
	let tasks = JSON.parse(localStorage.getItem('tasks'));

	for (let i = 0; i < tasks.length; i++) {
		if (tasks[i].title == title) {
			tasks.splice(i, 1);
		}
	}
	localStorage.setItem('tasks', JSON.stringify(tasks));
	getTask();
}

getTask();
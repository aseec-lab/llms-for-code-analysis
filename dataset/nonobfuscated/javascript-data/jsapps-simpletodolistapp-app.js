
let todoForm = document.getElementById("todoForm");
let todoInput = document.getElementById("todoInput");
let itemList = document.getElementById("itemList");

todoForm.addEventListener("submit", function (e) {
	e.preventDefault();
	if (todoInput.value == "") {
		inputoEmpty();
		return false;
	}
	addItem(todoInput.value);
});

function inputoEmpty() {
	console.log('empty');
}

function addItem(item) {
	let listItem = `<li>${item} <button onclick="removeItem(this)">x</button>`
	list.insertAdjacentHTML("afterbegin", listItem)
	todoInput.value = "";
	todoInput.focus();
}

function removeItem(itemToDelete) {
	itemToDelete.parentElement.remove();
}
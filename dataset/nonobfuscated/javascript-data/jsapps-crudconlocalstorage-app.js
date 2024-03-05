
const formularioUI = document.querySelector('#formulario');
const listaActividadesUI = document.querySelector('#listaActividades');
let arrayActividades = [];

const CrearTask = (actividad) => {
	let item = {
		actividad: actividad,
		estado: false
	}
	arrayActividades.push(item);
	return item;
}

const GuardarDB = () => {
	localStorage.setItem('task', JSON.stringify(arrayActividades))
	PintarDB()
}

const PintarDB = () => {
	listaActividadesUI.innerHTML = '';
	arrayActividades = JSON.parse(localStorage.getItem('task'))

	if (arrayActividades === null) {
		arrayActividades = [];
	} else {
		arrayActividades.forEach(element => {
			if (element.estado == true) {
				listaActividadesUI.innerHTML += `<div class="alert alert-success" role="alert"><i class="material-icons float-left">keyboard_arrow_right</i><span class="font-weight-bold">${element.actividad}</span> - ${element.estado}<span class="float-right"><i class="material-icons">done</i><i class="material-icons">delete_forever</i></span></div>`
			} else {
				listaActividadesUI.innerHTML += `<div class="alert alert-warning" role="alert"><i class="material-icons float-left">keyboard_arrow_right</i><span class="font-weight-bold">${element.actividad}</span> - ${element.estado}<span class="float-right"><i class="material-icons">done</i><i class="material-icons">delete_forever</i></span></div>`
			}
		})
	}
}

const EditarDB = (actividad) => {
	let indexArray = arrayActividades.findIndex((element) => {
		return element.actividad === actividad
	})
	arrayActividades[indexArray].estado = true;
	GuardarDB()
}

const EliminarDB = (actividad) => {
	let indexArray;
	arrayActividades.forEach((element, index) => {
		if (element.actividad === actividad) {
			indexArray = index
		}
	})
	arrayActividades.splice(indexArray, 1);
	GuardarDB();
}

formularioUI.addEventListener('submit', (e) => {
	e.preventDefault();
	let actividadUI = document.querySelector('#actividad').value;
	CrearTask(actividadUI);
	GuardarDB();
	formularioUI.reset();
})

document.addEventListener('DOMContentLoaded', PintarDB)

listaActividadesUI.addEventListener('click', (e) => {
	e.preventDefault()
	if (e.target.innerHTML === 'done' || e.target.innerHTML === 'delete_forever') {
		let text = e.path[2].childNodes[1].innerHTML;
		if (e.target.innerHTML === 'delete_forever') {
			EliminarDB(text)
		}
		if (e.target.innerHTML === 'done') {
			EditarDB(text)
		}
	}
})

const nombre = 'jaime'
localStorage.setItem('nombreUsuario', nombre);

const nombreLocalStorage = localStorage.getItem('nombreUsuario');
console.log(nombreLocalStorage);


const btnBotones = document.querySelector('#btnBotones');
const botones = document.querySelector('#botones');
const fondo = document.querySelector('#fondo');

(() => {
	const colorBG = localStorage.getItem('colorFondo')
	console.log(colorBG)
	if (colorBG === null) {
		fondo.className = 'bg-dark'
	} else {
		fondo.className = colorBG
	}
})();

(() => {
	btnBotones.addEventListener('click', agregarBotones);
	botones.addEventListener('click', delegacion);
})();

function agregarBotones(e) {

	e.preventDefault()
	botones.innerHTML =
		`
		<button class="btn btn-primary">primary</button>
		<button class="btn btn-secondary">secondary</button>
		<button class="btn btn-danger">danger</button>
		<button class="btn btn-success">success</button>
		<button class="btn btn-warning">warning</button>
	`
}

function delegacion(e) {

	e.preventDefault()
	console.log(e.target.classList[1])

	const colorBoton = e.target.classList[1]

	switch (colorBoton) {

		case ('btn-primary'):
			fondo.className = 'bg-primary'
			localStorage.setItem('colorFondo', 'bg-primary')
			break
		case ('btn-secondary'):
			fondo.className = 'bg-secondary'
			localStorage.setItem('colorFondo', 'bg-secondary')
			break
		case ('btn-danger'):
			fondo.className = 'bg-danger'
			localStorage.setItem('colorFondo', 'bg-danger')
			break
		case ('btn-success'):
			fondo.className = 'bg-success'
			localStorage.setItem('colorFondo', 'bg-success')
			break
		case ('btn-warning'):
			fondo.className = 'bg-warning'
			localStorage.setItem('colorFondo', 'bg-warning')
			break

	}
}

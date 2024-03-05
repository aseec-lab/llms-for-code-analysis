
const productos = [
	{ nombre: 'manzana', valor: 500 },
	{ nombre: 'pera', valor: 300 },
	{ nombre: 'mango', valor: 250 },
	{ nombre: 'naranja', valor: 300 },
	{ nombre: 'uvas', valor: 400 },
	{ nombre: 'ciruela', valor: 550 },
]
const formulario = document.querySelector('#formulario');
const boton = document.querySelector('#boton');
const resultado = document.querySelector('#resultado');

const filtrar = () => {

	resultado.innerHTML = '';

	const text = formulario.value.toLowerCase();

	for (let producto of productos) {
		let nombre = producto.nombre.toLocaleLowerCase();
		if (nombre.indexOf(text) !== -1) {
			resultado.innerHTML += 
				`<li>${producto.nombre} - Valor ${producto.valor}</li>`
		}
	}
	if (resultado.innerHTML === '') {
		resultado.innerHTML +=
			`<li>Producto no encontrado</li>`
	}
}

boton.addEventListener('click', filtrar);
formulario.addEventListener('keyup', filtrar);

filtrar();
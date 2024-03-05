console.log(':D');

let botonDolar = document.querySelector("#dolar")
let botonBitcoin = document.querySelector("#bitcoin")

botonDolar.addEventListener('click', function () {
	obtenerDatos('dolar')
})
botonBitcoin.addEventListener('click', function () {
	obtenerDatos('bitcoin')
})

function obtenerDatos(valor) {

	let url = `https://mindicador.cl/api/${valor}`

	const api = new XMLHttpRequest
	api.open('GET', url, true)
	api.send()
	api.onreadystatechange = respuesta
}


function respuesta() {

	if (this.status == 200 && this.readyState == 4) {

		let datos = JSON.parse(this.responseText)

		console.log(datos)

		let resultado = document.querySelector('#resultado')
		resultado.innerHTML = ''

		let i = 0
		for (let item of datos.serie) {
			i++
			resultado.innerHTML += `<li>Fecha: ${item.fecha.substring(0, 10)} - $${item.valor}</li>`
			if (i > 15) {
				break
			}
		}

	}
}
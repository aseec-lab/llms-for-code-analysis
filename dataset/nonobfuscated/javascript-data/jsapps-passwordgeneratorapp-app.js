(function () {

	
	var app = document.querySelector('#app');
	var inputCaracteres = document.querySelector('#numero-caracteres');
	var configuracion = {
		caracteres: parseInt(inputCaracteres.value),
		simbolos: true,
		numeros: true,
		mayusculas: true,
		minusculas: true
	}
	var caracteres = {
		numeros: '0 1 2 3 4 5 6 7 8 9',
		simbolos: '~ ! @ # $ % ^ & * ( ) _ + - = [ { } ] ; : , < . ? /',
		mayusculas: 'A B C D E F G H I J K L M N O P Q R S T U V W X Y Z',
		minusculas: 'a b c d e f g h i j k l m n o p q r s t u v w x y z',
	}

	
	app.addEventListener('submit', function (e) {
		e.preventDefault();
	})

	app.elements.namedItem('btn-mas-uno').addEventListener('click', () => {
		configuracion.caracteres++;
		inputCaracteres.value = configuracion.caracteres;
	})

	app.elements.namedItem('btn-menos-uno').addEventListener('click', () => {
		if (configuracion.caracteres > 1) {
			configuracion.caracteres--;
			inputCaracteres.value = configuracion.caracteres;
		}
	})

	
	app.elements.namedItem('btn-simbolos').addEventListener('click', function () {
		btnToggle(this);
		configuracion.simbolos = !configuracion.simbolos;
		console.log('simbolos ' + configuracion.simbolos)
	})

	app.elements.namedItem('btn-numeros').addEventListener('click', function () {
		btnToggle(this);
		configuracion.numeros = !configuracion.numeros;
		console.log('numeros ' + configuracion.numeros)
	})

	app.elements.namedItem('btn-mayuscula').addEventListener('click', function () {
		btnToggle(this);
		configuracion.mayusculas = !configuracion.mayusculas;
		console.log('mayusculas ' + configuracion.mayusculas)
	})

		let btnToggle = (elemento) => {
		elemento.classList.toggle('false');
		elemento.childNodes[0].nextElementSibling.classList.toggle('none')
	}

	
	app.elements.namedItem('btn-generar').addEventListener('click', function () {
		generatePassword();
	})

	let generatePassword = () => {
		let caracteresFinales = '';
		let password = '';

		for (propiedad in configuracion) {
			if (configuracion[propiedad] == true) {
				caracteresFinales += caracteres[propiedad] + ' ';
			}
		}
		caracteresFinales = caracteresFinales.trim();
		caracteresFinales = caracteresFinales.split(' ');

		for (var i = 0; i < configuracion.caracteres; i++) {
			password += caracteresFinales[Math.floor(Math.random() * caracteresFinales.length)];
		}
		app.elements.namedItem('input-password').value = password;
	}

	
	app.elements.namedItem('input-password').addEventListener('click', () => {
		copyPassword();
	})

	let copyPassword = () => {
		app.elements.namedItem('input-password').select();
		document.execCommand('copy');
		document.querySelector('.alerta-copiado').classList.add('active');
		setTimeout(function () {
			document.querySelector('.alerta-copiado').classList.remove('active');
		}, 2000);
	}

	generatePassword();
}())
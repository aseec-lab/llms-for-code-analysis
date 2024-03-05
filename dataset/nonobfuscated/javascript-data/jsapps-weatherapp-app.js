
(function () {

	let long;
	let lat;

	let locationTimezone = document.querySelector('.location-timezone');
	let temperatureSection = document.querySelector('.temperature');
	let temperatureDegree = document.querySelector('.temperature-degree');
	let temperatureSpan = document.querySelector('.temperature span');
	let temperatureDescription = document.querySelector('.temperature-description');

	if (navigator.geolocation) {

		navigator.geolocation.getCurrentPosition(position => {

			long = position.coords.longitude;
			lat = position.coords.latitude;

			const proxy = 'https://cors-anywhere.herokuapp.com/'
			const api = `${proxy}https://api.darksky.net/forecast/3ed2820bdef835d0923968060af681dd/${lat},${long}`;

			fetch(api)
				.then(response => {
					return response.json();
				})
				.then(data => {
					console.log(data)
					const {temperature, summary, icon} = data.currently;

										locationTimezone.textContent = data.timezone;
					temperatureDegree.textContent = temperature;
					temperatureDescription.textContent = summary;

										setIcons(icon, document.querySelector('.icon'));

										let celsius = (temperature - 32) * (5 / 9)

										temperatureSection.addEventListener('click', () => {
						if (temperatureSpan.textContent === 'F') {
							temperatureSpan.textContent = 'C';
							temperatureDegree.textContent = Math.floor(celsius);
						} else {
							temperatureSpan.textContent = 'F';
							temperatureDegree.textContent = temperature;
						}
					})
				})
		});
	}

	function setIcons(icon, iconID) {
		const skycons = new Skycons({
			color: "white"
		});
		const currentIcon = icon.replace(/-/g, "_").toUpperCase();
		skycons.play();
		return skycons.set(iconID, Skycons[currentIcon]);
	}

})();

const game = () => {
	let pScore = 0;
	let cScore = 0;

		const startGame = () => {
		const introScreen = document.querySelector('.intro');
		const playBtn = document.querySelector('.intro button');
		const match = document.querySelector('.match');

		playBtn.addEventListener('click', () => {
			introScreen.classList.add('fadeOut');
			match.classList.add('fadeIn');
		})
	}

		const playMatch = () => {
		const computerHand = document.querySelector('.computer-hand');
		const playerHand = document.querySelector('.player-hand');
		const options = document.querySelectorAll('.options button');

		const hands = document.querySelectorAll('.hands img');

		hands.forEach((hand) => {
			hand.addEventListener('animationend', function () {
				this.style.animation = ''
			})
		})

				const computerOptions = ['rock', 'paper', 'scissors'];

		options.forEach((option) => {
			option.addEventListener('click', function () {
								const computerNumber = Math.floor(Math.random() * 3);
				const computerChoise = computerOptions[computerNumber];

				setTimeout(() => {
										compareHands(this.textContent, computerChoise);
										playerHand.src = `./imgs/${this.textContent}.png`
					computerHand.src = `./imgs/${computerChoise}.png`
				}, 2000);

								playerHand.style.animation = 'shakePlayer 2s ease';
				computerHand.style.animation = 'shakeComputer 2s ease';
			})
		})
	}

	const compareHands = (playerChoise, computerChoise) => {
		const winner = document.querySelector('.winner');

				if (playerChoise === computerChoise) {
			winner.textContent = "It is a tie";
			return;
		}
				if (playerChoise === 'rock') {
			if (computerChoise === 'scissors') {
				winner.textContent = 'Player Wins!'
				pScore++;
				updateScore();
				return;
			} else {
				winner.textContent = 'Computer Wins';
				cScore++;
				updateScore();
				return;
			}
		}
				if (playerChoise === 'paper') {
			if (computerChoise === 'rock') {
				winner.textContent = 'Player Wins!'
				pScore++;
				updateScore();
				return;
			} else {
				winner.textContent = 'Computer Wins';
				cScore++;
				updateScore();
				return;
			}
		}
				if (playerChoise === 'scissors') {
			if (computerChoise === 'paper') {
				winner.textContent = 'Player Wins!'
				pScore++;
				updateScore();
				return;
			} else {
				winner.textContent = 'Computer Wins';
				cScore++;
				updateScore();
				return;
			}
		}
	}

	const updateScore = () => {
		const playerScore = document.querySelector('.player-score p');
		const computerScore = document.querySelector('.computer-score p');
		playerScore.textContent = pScore;
		computerScore.textContent = cScore;
	}


		startGame();
	playMatch();
}

game();
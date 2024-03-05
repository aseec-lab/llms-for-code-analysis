const endpoint = 'https://raw.githubusercontent.com/jaiimeriios/js-apps/master/17_type-ahead/data.json'
const getData = [];
const searchInput = document.querySelector('#search-input');
const searchList = document.querySelector('#show-suggestions');

searchInput.addEventListener('keyup', displayMatches);

fetch(endpoint).then((dataResponse) => {
    dataResponse.json().then((data) => {
        getData.push(...data);
    });
});

function findMatches(wordToMatch, getData) {
    return getData.filter((data) => {
        const regex = new RegExp(wordToMatch, 'gi');
        return data.title.match(regex) || data.description.match(regex);
    });
}

function displayMatches() {

    const matchArray = findMatches(this.value, getData);
    const html = matchArray.map((place) => {

        const regexWord = new RegExp(this.value, 'gi');
        const title = place.title.replace(regexWord, `<span class="hl">${this.value}</span>`);
        const description = place.description.replace(regexWord, `<span class="hl">${this.value}</span>`);

        return `<li><h6>${title}</h6> <p>${description}</li>`;

    }).join(''); 
    searchList.innerHTML = html;

    if (searchInput.value == '') {
        searchList.innerHTML = ''
    }
}


const button = document.querySelector('#open-search-form')
const searchForm = document.querySelector('.search-form')

button.addEventListener('click', () => {
    searchForm.classList.toggle('is-open')
})
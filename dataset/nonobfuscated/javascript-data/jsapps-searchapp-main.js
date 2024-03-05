import {
    setSearchFocus,
    showClearTextButton,
    clearSearchText,
    clearPushListener,
} from "./modules/search-bar.js"
import {
    getSearchTerm,
    retrieveSearchResults,
} from "./modules/data-functions.js"
import {
    deleteSearchResults,
    buildSearchResults,
    clearStatsLine,
    setStatsLine,
} from "./modules/search-results.js"

document.addEventListener("readystatechange", (event) => {
    if (event.target.readyState === "complete") {
        initApp()
    }
})

const initApp = () => {
    setSearchFocus()

    document.getElementById("search").addEventListener("input", showClearTextButton)

    const clear = document.getElementById("clear")
    clear.addEventListener('click', clearSearchText)
    clear.addEventListener('keydown', clearPushListener)



    const form = document.getElementById("search-bar")
    form.addEventListener("submit", submitSearch)
}

const submitSearch = (e) => {
    e.preventDefault()
    deleteSearchResults()
    processSearch()
    setSearchFocus()
}

const processSearch = async () => {
    clearStatsLine();
    const searchTerm = getSearchTerm();
    if (searchTerm === "") return;     const resultArray = await retrieveSearchResults(searchTerm);
    if (resultArray.length) buildSearchResults(resultArray);
    setStatsLine(resultArray.length);
  };
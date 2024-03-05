export const setSearchFocus = () => {
    document.getElementById("search").focus()
}

export const showClearTextButton = () => {
    const search = document.getElementById("search")
    const clear = document.getElementById("clear")

    if (search.value.length > 0) {
        clear.classList.remove("none")
        clear.classList.add("flex")
    } else {
        clear.classList.remove("flex")
        clear.classList.add("none")
    }
}

export const clearSearchText = (e) => {
    e.preventDefault()
    document.getElementById("search").value = ""
    const clear = document.getElementById("clear")
    clear.classList.remove("flex")
    clear.classList.add("none")
    setSearchFocus()
}

export const clearPushListener = (e) => {
    console.log(e.key)
    if (e.key === "Enter" || e.key === "Space") {
        e.preventDefault()
        document.getElementById("clear").click()
    }
}
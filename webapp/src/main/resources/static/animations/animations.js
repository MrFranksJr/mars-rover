export { showLogo, moveLogo, showMainPanels }


const main = document.getElementById('main')
const footer = document.getElementById('footer')
const header = document.getElementById('header')
const applicationLogo = document.getElementById('applicationLogo')
const logoWrapper = document.getElementById('logoWrapper')
const mainPanels = document.getElementById('main-panels')



function showLogo() {
    setTimeout(() => {
        main.style.opacity = '100'
        footer.style.opacity = '100'
    }, 200);
}

function moveLogo() {
    setTimeout(() => {
        header.style.transform = 'unset'
        applicationLogo.style.transform = 'unset'
        logoWrapper.style.gap = '20px'
    }, 1500);
}

function showMainPanels() {
    setTimeout(() => {
        mainPanels.style.opacity = '100'
    }, 1800);
}
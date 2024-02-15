export { showLogoAnimation, moveLogoAnimation, showMainPanelsAnimation }


const main = document.getElementById('main')
const footer = document.getElementById('footer')
const header = document.getElementById('header')
const applicationLogo = document.getElementById('applicationLogo')
const logoWrapper = document.getElementById('logoWrapper')
const mainPanels = document.getElementById('main-panels')



function showLogoAnimation() {
    setTimeout(() => {
        main.style.opacity = '100'
        footer.style.opacity = '100'
    }, 200);
}

function moveLogoAnimation() {
    setTimeout(() => {
        header.style.transform = 'unset'
        applicationLogo.style.transform = 'unset'
        logoWrapper.style.gap = '10px'
    }, 2000);
}

function showMainPanelsAnimation() {
    setTimeout(() => {
        mainPanels.style.opacity = '100'
    }, 2300);
}
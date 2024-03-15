export { showAllAnimations, disableAnimations }


const main = document.getElementById('main')
const footer = document.getElementById('footer')
const header = document.getElementById('header')
const applicationLogo = document.getElementById('applicationLogo')
const logoWrapper = document.getElementById('logoWrapper')
const mainPanels = document.getElementById('main-panels')

function showAllAnimations() {
    showLogoAnimation()
}

function showLogoAnimation() {
    setTimeout(() => {
        main.style.opacity = '100'
        footer.style.opacity = '100'
        moveLogoAnimation()
    }, 200);
}

function moveLogoAnimation() {
    setTimeout(() => {
        header.style.transform = 'unset'
        applicationLogo.style.transform = 'unset'
        logoWrapper.style.gap = '10px'
        showMainPanelsAnimation()
    }, 2500);
}

function showMainPanelsAnimation() {
    setTimeout(() => {
        mainPanels.style.opacity = '100'
    }, 300);
}

function disableAnimations() {
    main.style.opacity = '100'
    footer.style.opacity = '100'
    header.style.transform = 'unset'
    applicationLogo.style.transform = 'unset'
    logoWrapper.style.gap = '10px'
    mainPanels.style.opacity = '100'
}
export { showInfo }
function showInfo() {
    document.getElementById('main').classList.toggle('main-blurred')
    document.getElementById('info-modal').classList.toggle('info-modal-visible')
}
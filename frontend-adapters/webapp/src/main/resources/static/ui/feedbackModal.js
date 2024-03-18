import { modalDiv } from "./htmlElements.js";

export { modalError, moveModal }


function moveModal() {
    modalDiv.classList.add('activeModal');
    setTimeout(() => {
        modalDiv.classList.remove('activeModal');
    }, "2500");
}

function modalError() {
    modalDiv.classList.add('errorModal');
    setTimeout(() => {
        modalDiv.classList.remove('errorModal');
    }, "2500");
}
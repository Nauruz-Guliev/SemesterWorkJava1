let checkMessage = function () {
    let errorDiv = document.getElementById('popupMessageBody');
    let exceptionDiv = document.getElementById('popupMessageTitle');
    if(errorDiv.innerText !== "" && exceptionDiv.innerText !== "") {
        showPopup(errorDiv.innerText, exceptionDiv.innerText);
        errorDiv.innerText = "";
        exceptionDiv.innerText = "";
    }
};


window.setInterval(checkMessage, 100);


const showPopup = (message, title) => {
    let myModal = new bootstrap.Modal(document.getElementById("modal"), {});
    document.getElementById('message-body').innerText = message;
    document.getElementById('modal-title').innerHTML = title;
    myModal.show();
}

let checkMessage = function () {
    let errorDiv = document.getElementById('popupMessageBody');
    let exceptionDiv = document.getElementById('popupMessageTitle');
    if(errorDiv.innerText !== "" && exceptionDiv.innerText !== "") {
        showPopup(errorDiv.innerText, exceptionDiv.innerText, false);
        errorDiv.innerText = "";
        exceptionDiv.innerText = "";
    }

};


window.setInterval(checkMessage, 100);


const showPopup = (message, title, showButton) => {
    let deleteUserButton = document.getElementById('btnDeleteUserConfirmed');
    if(showButton) {
        deleteUserButton.style.display = "block";
    }
    let myModal = new bootstrap.Modal(document.getElementById("modal"), {});
    document.getElementById('message-body').innerText = message;
    document.getElementById('modal-title').innerHTML = title;
    myModal.show();
}


function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}

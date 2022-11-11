window.onload = function () {
    let deleteUserButton = document.getElementById('btnDeleteUser');
    let deleteUserButtonConfirmed = document.getElementById('btnDeleteUserConfirmed');
    if(deleteUserButton !== null) {
        deleteUserButton.addEventListener('click', deleteUserConfirmation, false);
    }
    if(deleteUserButtonConfirmed !== null) {
        deleteUserButtonConfirmed.addEventListener('click', deleteUserPermanently, false);
    }
}

function deleteUserConfirmation(){
    showPopup("Permanently delete?", "Account deletion", true);
}
function deleteUserPermanently(){
    window.location.href = "http://localhost:8888/profile/security?confirmed=true";
}

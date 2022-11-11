let tagCount = 0;
window.onload = function () {
    let btnAddTag = document.getElementById('btn-add-tag');
    if (btnAddTag != null) {
        btnAddTag.addEventListener('click', addTag, false);
    }
}

function addTag() {
    debugger;
    if (tagCount < 4) {
        let body = document.getElementById('create-article');
        let tagFieldClone = document.getElementById('tag-field').cloneNode(true);
        tagFieldClone.children['tag'].name = 'tag' + tagCount;
        tagFieldClone.children['tag'].value = "";
        body.append(tagFieldClone);
        tagCount++;
    } else {
        showPopup("Cannot create that many tags!", "Error");
    }
}
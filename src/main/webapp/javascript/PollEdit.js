function removeChoice(button) {
    var choiceCount = document.getElementsByClassName("form-group card").length;

    console.log(choiceCount);

    if (choiceCount <= 2) return;

    var choiceBlock = button.parentElement.parentElement.parentElement;

    choiceBlock.remove();

    reindexChoices();
}

function addChoice(button) {
    var choiceBlock = document.getElementsByClassName("form-group card")[0];
    var choiceParent = choiceBlock.parentElement;
    var newChoice = choiceBlock.cloneNode(true);

    var fieldParent = newChoice.lastElementChild;
    for (var i = 1; i < fieldParent.children.length; i += 2) {
        fieldParent.children[i].firstElementChild.value = "";
    }

    //var labelHold

    choiceParent.insertBefore(newChoice, button);

    reindexChoices();
}

function reindexChoices() {
    var choiceBlocks = document.getElementsByClassName("form-group card");
    for (var i = 0; i < choiceBlocks.length; i++) {
        choiceBlocks[i].firstElementChild.firstElementChild.innerHTML = "Choice #" + (i + 1);
    }
}
function showSelectTagList() {
    $("#selectTagList").show();
}

function hideSelectTagList() {
    $("#selectTagList").hide();
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previousTag = $("#tag").val();
    if (previousTag.indexOf(value) == -1) {
        if (previousTag) {
            $("#tag").val(previousTag + "," + value);
        } else {
            $("#tag").val(value);
        }
    }
}

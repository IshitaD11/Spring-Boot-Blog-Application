const tagsInput = document.getElementById('tags');
let selectedTags = [];

function initializeTagSelection() {
    const tagElements = document.querySelectorAll('#predefined-tags .tag');

    tagElements.forEach(tagElement => {
        tagElement.addEventListener('click', function () {
            const tagName = tagElement.innerText.trim();
            const tagId = tagElement.getAttribute('data-tag-id');

            if (tagElement.classList.contains('selected')) {
                tagElement.classList.remove('selected');
                selectedTags = selectedTags.filter(tag => tag.id !== tagId);
            } else {
                tagElement.classList.add('selected');
                selectedTags.push({ id: tagId, name: tagName });
            }

            updateHiddenInput();
        });
    });
}

function updateHiddenInput() {
    tagsInput.value = selectedTags.map(tag => tag.id).join(',');
}

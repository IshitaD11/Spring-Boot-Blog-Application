// tags-filter.js

let selectedTags = [];

function initializeTagSelection() {
    const tagElements = document.querySelectorAll('#tags-filter .tag');

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

            filterPostsByTags();
        });
    });
}

function filterPostsByTags() {
    const urlParams = new URLSearchParams(window.location.search);
    const tagIds = selectedTags.map(tag => tag.id);
    if (tagIds.length > 0) {
        urlParams.set('tagIds', tagIds.join(','));
    } else {
        urlParams.delete('tagIds');
    }
    window.location.search = urlParams.toString();
}

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

    // Highlight already selected tags on page load (if any)
    const urlParams = new URLSearchParams(window.location.search);
    const selectedTagIds = urlParams.get('tagIds');
    if (selectedTagIds) {
        selectedTags = selectedTagIds.split(',').map(tagId => {
            const tagElement = document.querySelector(`#tags-filter .tag[data-tag-id="${tagId}"]`);
            if (tagElement) {
                tagElement.classList.add('selected');
            }
            return { id: tagId, name: tagElement ? tagElement.innerText.trim() : '' };
        });
    }
}

function filterPostsByTags() {
    const form = document.getElementById('tagsFilterForm');
    const tagIdsInput = document.getElementById('tagIdsInput');
    const tagIds = selectedTags.map(tag => tag.id);

    // Update the hidden input with the selected tag IDs
    tagIdsInput.value = tagIds.join(',');

    // Update the form's action to point to the home page with query parameters
    form.action = `/?tagIds=${tagIds.join(',')}`;

    // Submit the form
    form.submit();
}

document.addEventListener('DOMContentLoaded', function () {
    initializeTagSelection();
});

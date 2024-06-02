document.addEventListener('DOMContentLoaded', function() {
    initializeTagSelection();
});

function initializeTagSelection() {
    const tagsInput = document.getElementById('tags');
    let selectedTags = [];

    const tagElements = document.querySelectorAll('#predefined-tags .tag');

    tagElements.forEach(tagElement => {
        const tagId = tagElement.getAttribute('data-tag-id');
        const tagName = tagElement.innerText.trim();

        // Initialize selectedTags with already selected tags
        if (tagElement.classList.contains('selected')) {
            selectedTags.push({ id: tagId, name: tagName });
        }

        tagElement.addEventListener('click', function () {
            if (tagElement.classList.contains('selected')) {
                tagElement.classList.remove('selected');
                selectedTags = selectedTags.filter(tag => tag.id !== tagId);
            } else {
                tagElement.classList.add('selected');
                selectedTags.push({ id: tagId, name: tagName });
            }

            updateHiddenInput(tagsInput, selectedTags);
        });
    });

    updateHiddenInput(tagsInput, selectedTags);
}

function updateHiddenInput(tagsInput, selectedTags) {
    if (tagsInput) {
        tagsInput.value = selectedTags.map(tag => tag.id).join(',');
        console.log("Selected Tags:", selectedTags); // Debug: Log selected tags
        console.log("Hidden Input Value:", tagsInput.value); // Debug: Log hidden input value
    } else {
        console.error("Element with ID 'tags' not found"); // Debug: Log error if element is not found
    }
}

// search.js
document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.getElementById('postTitleInput');
    const suggestionsBox = document.getElementById('suggestions');

    searchInput.addEventListener('input', function () {
        const query = searchInput.value;
        if (query.length > 2) {
            fetch(`/search/suggestions?query=${query}`)
                .then(response => response.json())
                .then(data => {
                    suggestionsBox.innerHTML = '';
                    data.forEach(post => {
                        const suggestionItem = document.createElement('div');
                        suggestionItem.textContent = post.title;
                        suggestionItem.className = 'suggestion-item';
                        suggestionItem.addEventListener('click', () => {
                            window.location.href = `/posts/${post.slug}`;
                        });
                        suggestionsBox.appendChild(suggestionItem);
                    });
                });
        } else {
            suggestionsBox.innerHTML = '';
        }
    });
});

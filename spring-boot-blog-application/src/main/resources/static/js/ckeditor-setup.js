// ckeditor-setup.js

class MyUploadAdapter {
    constructor(loader) {
        this.loader = loader;
    }

    upload() {
        return this.loader.file
            .then(file => new Promise((resolve, reject) => {
                this._uploadFile(file, resolve, reject);
            }));
    }

    _uploadFile(file, resolve, reject) {
        const data = new FormData();
        data.append('upload', file);

        fetch('/upload', {
            method: 'POST',
            body: data,
            headers: {
                'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').getAttribute('content')
            }
        })
            .then(response => response.json())
            .then(response => {
                if (response.url) {
                    resolve({
                        default: response.url
                    });
                } else {
                    reject(response.error || 'Upload failed');
                }
            })
            .catch(reject);
    }

    abort() {
        // Implement if necessary
    }
}

function MyCustomUploadAdapterPlugin(editor) {
    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
        return new MyUploadAdapter(loader);
    };
}

function initializeCKEditor(fieldId) {
    ClassicEditor
        .create(document.querySelector(`#${fieldId}`), {
            extraPlugins: [MyCustomUploadAdapterPlugin]
        })
        .catch(error => {
            console.error(error);
        });
}

function htmlEncode(str) {
    return str.replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#39;')
        .replace(/\t/g, '&nbsp;&nbsp;&nbsp;&nbsp;'); // Encode tabs to spaces for indentation
}

function extractCodeBlockDetails(codeBlock) {
    const div = document.createElement('div');
    div.innerHTML = codeBlock;
    const preTag = div.querySelector('pre code');
    if (preTag) {
        const languageClass = preTag.className.match(/language-([^\s]+)/);
        const language = languageClass ? languageClass[1] : '';
        const code = preTag.innerHTML
            .replace(/&lt;/g, '<')
            .replace(/&gt;/g, '>')
            .replace(/&amp;/g, '&')
            .replace(/&quot;/g, '"')
            .replace(/&#39;/g, "'");
        return { language, code };
    }
    return { language: '', code: '' };
}

document.addEventListener('DOMContentLoaded', function () {
    const newPostForm = document.getElementById('new-post-form');
    const editPostForm = document.getElementById('edit-post-form');
    const form = newPostForm || editPostForm;

    if (form) {
        initializeCKEditor('content');
        initializeCKEditor('problemStatement');

        if (editPostForm) {
            const codeBlockField = document.querySelector('#codeBlock');
            const codeBlockLanguageField = document.querySelector('#codeBlockLanguage');
            const codeBlockContent = codeBlockField.value;

            const { language, code } = extractCodeBlockDetails(codeBlockContent);
            codeBlockField.value = code;
            codeBlockLanguageField.value = language;
        }

        form.addEventListener('submit', function (event) {
            const codeBlock = document.querySelector('#codeBlock');
            const codeBlockLanguage = document.querySelector('#codeBlockLanguage').value;
            if (codeBlock) {
                const code = codeBlock.value;
                codeBlock.value = `<pre><code class="language-${codeBlockLanguage}">${htmlEncode(code)}</code></pre>`;
            }

        });
    }

    // Add pageshow event listener to force reload if loaded from cache
    window.addEventListener('pageshow', function(event) {
        if (event.persisted) {
            window.location.reload();
        }
    });

});
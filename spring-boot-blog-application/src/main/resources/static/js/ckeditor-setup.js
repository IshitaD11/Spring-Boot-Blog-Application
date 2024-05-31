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

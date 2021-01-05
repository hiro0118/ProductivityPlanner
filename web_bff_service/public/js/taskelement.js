class taskElement extends HTMLElement {
    constructor() {
        super();
    }

    get title() {
        return this.getAttribute('title');
    }

    set title(newTitle) {
        this.setAttribute('title', newTitle);
    }

    get date() {
        return this.getAttribute('date');
    }

    set date(newDate) {
        this.setAttribute('date', newDate);
    }

    connectedCallback() {
        this.innerHTML = [
            '<div class="card mb-3">',
            '<div class="card-body">',
            '<h5 class="card-title">', this.title, '</h5>',
            '<h6 class="card-subtitle mb-2 text-muted">', this.date, '</h6>',
            '<p class="card-text">Test</p>',
            '<a href="#" class="btn btn-primary">Edit</a>',
            '</div>',
            '</div>'
        ].join(' ');
    }
}
customElements.define('task-element', taskElement);
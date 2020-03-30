import { html, LitElement } from 'lit-element';

/**
 * Based on https://github.com/hamedasemi/lit-element-router/blob/258c2451c36da8bdefaa81aa0d3c49f4f9e67355/lit-element-router.js#L110-L149
 */
export class PollRouter extends LitElement {
  static get properties() {
    return {
      activeRoute: {
        type: String,
        attribute: 'active-route',
      },
    };
  }

  constructor() {
    super();
    this.activeRoute = '';
  }

  attributeChangedCallback(name, old, value) {
    super.attributeChangedCallback(name, old, value);
    if (name === 'active-route') {
      this.outlet();
    }
  }

  connectedCallback() {
    super.connectedCallback();

    setTimeout(() => {
      this.outlet();
    });
  }

  /* eslint-disable no-param-reassign */
  outlet() {
    Array.from(this.querySelectorAll(`[route]`)).forEach(active => {
      active.style.display = 'none';
    });
    if (this.activeRoute) {
      Array.from(this.querySelectorAll(`[route~=${this.activeRoute}]`)).forEach(active => {
        active.style.display = '';
      });
    }
  }
  /* eslint-enable no-param-reassign */

  render() {
    return html`
      <slot></slot>
    `;
  }
}

customElements.define('poll-router', PollRouter);

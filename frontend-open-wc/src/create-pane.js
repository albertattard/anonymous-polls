import { html, LitElement } from 'lit-element';
import { navigateToHome } from './poll-routes.js';

export class CreatePane extends LitElement {
  render() {
    return html`
      <h1>Create a new Anonymous Polls</h1>
      <button @click=${navigateToHome}>Home</button>
    `;
  }
}

customElements.define('create-pane', CreatePane);

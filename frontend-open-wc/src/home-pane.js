import { html, LitElement } from 'lit-element';
import { navigateToCreate } from './poll-routes.js';
import { PollGateway } from './poll-gateway.js';

export class HomePane extends LitElement {
  static get properties() {
    return {
      numberOfPolls: { type: String },
    };
  }

  constructor() {
    super();
    this.numberOfPolls = 'Pending...';

    /* TODO: Pass this as a parameter so that we can mock it for tests */
    this.gateway = new PollGateway();
  }

  connectedCallback() {
    super.connectedCallback();
    this.updateNumberOfPolls();
  }

  updateNumberOfPolls() {
    this.gateway.count().then(count => {
      if (count.error) {
        this.numberOfPolls = 'Failed to retrieve the number of polls';
      } else if (count.total === 0) {
        this.numberOfPolls = 'No polls found!!';
      } else {
        this.numberOfPolls = `Number of polls ${count.total}`;
      }
    });
  }

  render() {
    return html`
      <h1>Anonymous Polls</h1>
      <div class="count">${this.numberOfPolls}</div>
      <button @click=${navigateToCreate}>Create a new anonymous poll</button>
    `;
  }
}

customElements.define('home-pane', HomePane);

import { css, html, LitElement } from 'lit-element';
import { openWcLogo } from './open-wc-logo.js';
import { PollGateway } from './poll-gateway.js';
import './poll-router.js';

export class AnonymousPoll extends LitElement {
  static get properties() {
    return {
      numberOfPolls: { type: String },
      route: { type: String },
    };
  }

  static get styles() {
    return css`
      :host {
        min-height: 100vh;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: flex-start;
        font-size: calc(10px + 2vmin);
        color: #1a2b42;
        max-width: 960px;
        margin: 0 auto;
        text-align: center;
      }

      main {
        flex-grow: 1;
      }

      main span {
        margin: 12px;
        display: block;
      }

      .logo > svg {
        margin-top: 36px;
        animation: app-logo-spin infinite 20s linear;
      }

      @keyframes app-logo-spin {
        from {
          transform: rotate(0deg);
        }
        to {
          transform: rotate(360deg);
        }
      }

      .app-footer {
        font-size: calc(12px + 0.5vmin);
        align-items: center;
      }

      .app-footer a {
        margin-left: 5px;
      }
    `;
  }

  static routeFromHash() {
    const { hash } = window.location;
    if (hash === '#create') {
      return 'create';
    }

    return 'home';
  }

  static navigateTo(hash) {
    window.location.hash = hash;
  }

  constructor() {
    super();
    this.numberOfPolls = 'Pending...';
    this.route = AnonymousPoll.routeFromHash();

    /* TODO: Pass this as a parameter so that we can mock it for tests */
    this.gateway = new PollGateway();

    window.onhashchange = () => {
      this.locationHashChanged();
    };
  }

  connectedCallback() {
    super.connectedCallback();
    this.updateNumberOfPolls();
  }

  locationHashChanged() {
    this.route = AnonymousPoll.routeFromHash();
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
      <main>
        <div class="logo">${openWcLogo}</div>
        <h1>Anonymous Polls</h1>
        <span class="coming-soon">Coming soon to a browser close to you</span>
        <span class="count">${this.numberOfPolls}</span>

        <poll-router active-route="${this.route}">
          <div route="home">Home View</div>
          <div route="create">Creat View</div>
        </poll-router>

        <button @click=${() => AnonymousPoll.navigateTo('home')}>Home</button>
        <button @click=${() => AnonymousPoll.navigateTo('create')}>Create</button>
      </main>

      <p class="app-footer">
        Made with love and passion by
        <a
          target="_blank"
          rel="noopener noreferrer"
          href="https://github.com/albertattard/anonymous-polls"
          >Albert Attard</a
        >.
      </p>
    `;
  }
}

customElements.define('anonymous-poll', AnonymousPoll);

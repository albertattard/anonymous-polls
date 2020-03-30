import { css, html, LitElement } from 'lit-element';
import { openWcLogo } from './open-wc-logo.js';
import { navigateToHome, HOME, CREATE } from './poll-routes.js';
import './poll-router.js';
import './home-pane.js';
import './create-pane.js';

export class AnonymousPoll extends LitElement {
  static get properties() {
    return {
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
        margin-top: 24px;
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

  static routeFromHash(hash = window.location.hash) {
    if (hash === '#create') {
      return 'create';
    }

    return 'home';
  }

  constructor() {
    super();
    this.route = AnonymousPoll.routeFromHash();

    window.onhashchange = () => {
      this.route = AnonymousPoll.routeFromHash();
    };
  }

  render() {
    return html`
      <main>
        <div class="logo" @click=${navigateToHome}>${openWcLogo}</div>

        <poll-router active-route="${this.route}">
          <home-pane route=${HOME}></home-pane>
          <create-pane route=${CREATE}></create-pane>
        </poll-router>
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

import { css, html, LitElement } from 'lit-element';
import { openWcLogo } from './open-wc-logo.js';

export class AnonymousPoll extends LitElement {
  static get properties() {
    return {
      numberOfPolls: { type: String },
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

  constructor() {
    super();
    this.numberOfPolls = 'Pending...';
  }

  connectedCallback() {
    super.connectedCallback();

    fetch('/poll/count')
      .then(response => {
        if (response.status === 200) {
          return response.json();
        }

        return { total: -1 };
      })
      .then(data => {
        if (data.total === 0) {
          this.numberOfPolls = 'No polls found!!';
        } else if (data.total > 0) {
          this.numberOfPolls = `Number of polls ${data.total}`;
        } else {
          this.numberOfPolls = 'Failed to retrieve the number of polls';
        }
      });
  }

  render() {
    return html`
      <main>
        <div class="logo">${openWcLogo}</div>
        <h1>Anonymous Polls</h1>
        <span>Coming soon to a browser close to you</span>
        <span>${this.numberOfPolls}</span>
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

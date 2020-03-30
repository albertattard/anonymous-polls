import { expect, fixture, html } from '@open-wc/testing';
import '../src/anonymous-poll.js';

describe('Polls', () => {
  let element;
  beforeEach(async () => {
    element = await fixture(html`
      <anonymous-poll></anonymous-poll>
    `);
  });

  describe('General', () => {
    it('renders footer', () => {
      const footer = element.shadowRoot.querySelector('p[class=app-footer]');
      expect(footer).to.exist;
      expect(footer.textContent).to.contain('Made with love and passion by');
    });

    it('passes the a11y audit', async () => {
      await expect(element).shadowDom.to.be.accessible();
    });
  });
});

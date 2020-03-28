import { expect, fixture, html } from '@open-wc/testing';
import sinon from 'sinon';
import '../src/anonymous-poll.js';

describe('Anonymous Poll', () => {
  let element;
  beforeEach(async () => {
    element = await fixture(html`
      <anonymous-poll></anonymous-poll>
    `);
  });

  it('renders a h1', () => {
    const h1 = element.shadowRoot.querySelector('h1');
    expect(h1).to.exist;
    expect(h1.textContent).to.equal('Anonymous Polls');
  });

  it('passes the a11y audit', async () => {
    await expect(element).shadowDom.to.be.accessible();
  });

  it('displays an error when total is less than 0', async () => {
    element.gateway.count = sinon.stub();
    element.gateway.count.resolves({ total: -1 });

    await element.updateNumberOfPolls();

    expect(element.numberOfPolls).to.equal('Failed to retrieve the number of polls');
  });

  it('displays a custom message when total is 0', async () => {
    element.gateway.count = sinon.stub();
    element.gateway.count.resolves({ total: 0 });

    await element.updateNumberOfPolls();

    expect(element.numberOfPolls).to.equal('No polls found!!');
  });

  it('displays the number of polls when total is greater than 0', async () => {
    element.gateway.count = sinon.stub();
    element.gateway.count.resolves({ total: 10 });

    await element.updateNumberOfPolls();

    expect(element.numberOfPolls).to.equal('Number of polls 10');
  });
});

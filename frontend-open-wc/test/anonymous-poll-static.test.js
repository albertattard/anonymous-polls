import { expect } from '@open-wc/testing';
import { AnonymousPoll } from '../src/anonymous-poll.js';

describe('Polls Static Functions', () => {
  it('returns create when the location hash is #create', async () => {
    const route = AnonymousPoll.routeFromHash('#create');
    expect(route).to.equal('create');
  });

  it('returns home when the location hash is something unknown', async () => {
    const route = AnonymousPoll.routeFromHash('#somewhere');
    expect(route).to.equal('home');
  });
});

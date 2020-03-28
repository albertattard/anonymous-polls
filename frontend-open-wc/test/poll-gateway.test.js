import sinon from 'sinon';
import { expect } from '@open-wc/testing';
import { PollGateway } from '../src/poll-gateway.js';

describe('Anonymous Poll', () => {
  it('should return the total received', async () => {
    const response = {
      status: 200,
    };
    response.json = () =>
      new Promise(resolve => {
        resolve({ total: 42 });
      });

    const gateway = new PollGateway();
    gateway.get = sinon.stub();
    gateway.get.resolves(response);

    const count = await gateway.count();

    expect(count.false).to.be.undefined;
    expect(count.total).to.equal(42);
    expect(gateway.get.calledWith('count')).to.be.true;
  });

  it('should return error when a non 200 response is received', async () => {
    const response = {
      status: 404,
    };

    const gateway = new PollGateway();
    gateway.get = sinon.stub();
    gateway.get.resolves(response);

    const count = await gateway.count();

    expect(count.error).to.be.true;
    expect(gateway.get.calledWith('count')).to.be.true;
  });
});

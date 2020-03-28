export class PollGateway {
  constructor() {
    this.basePath = '/poll';
  }

  count() {
    return fetch(`${this.basePath}/count`).then(response => {
      if (response.status === 200) {
        return response.json();
      }

      return { total: -1 };
    });
  }
}

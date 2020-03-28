export class PollGateway {
  constructor() {
    this.basePath = '/poll';
  }

  get(path) {
    return fetch(`${this.basePath}/${path}`);
  }

  count() {
    return this.get('count').then(response => {
      if (response.status === 200) {
        return response.json();
      }

      return { error: true };
    });
  }
}

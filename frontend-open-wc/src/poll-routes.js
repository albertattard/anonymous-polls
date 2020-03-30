export const HOME = 'home';
export const CREATE = 'create';

function navigateTo(hash) {
  window.location.hash = hash;
}

export function navigateToHome() {
  navigateTo(HOME);
}

export function navigateToCreate() {
  navigateTo(CREATE);
}

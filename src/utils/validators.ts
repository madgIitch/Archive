// src/utils/validators.ts
export function isValidEmail(email: string) {
  return /\S+@\S+\.\S+/.test(email);
}

export function isValidPassword(password: string) {
  return password.length >= 6;
}

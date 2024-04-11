module.exports = {
  root: true,
  env: { browser: true, es2020: true },
  extends: [
    'eslint:recommended',
    'plugin:@typescript-eslint/recommended',
    'plugin:react-hooks/recommended',
  ],
  ignorePatterns: ['dist', '.eslintrc.cjs'],
  parser: '@typescript-eslint/parser',
  plugins: ['react-refresh'],
  rules: {
    "@typescript-eslint/no-unused-vars": "off", // Ignore unused variables warning
    "@typescript-eslint/no-unsafe-member-access": "off", // Ignore unsafe member access warning
    "@typescript-eslint/no-unsafe-call": "off", // Ignore unsafe call warning
    'react-refresh/only-export-components': [
      'warn',
      { allowConstantExport: true },
    ],
  },
}

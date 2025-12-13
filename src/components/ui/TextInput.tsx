// src/components/ui/TextInput.tsx
import React from 'react';
import { StyleSheet, Text, TextInput as RNTextInput, View, TextInputProps } from 'react-native';
import { colors, spacing } from '../../theme';

type Props = TextInputProps & {
  label?: string;
  errorMessage?: string;
};

export const TextInput: React.FC<Props> = ({ label, errorMessage, ...rest }) => {
  const hasError = !!errorMessage;

  return (
    <View style={styles.container}>
      {label && <Text style={styles.label}>{label}</Text>}
      <RNTextInput
        style={[styles.input, hasError && styles.inputError]}
        placeholderTextColor={colors.textSecondary}
        {...rest}
      />
      {hasError && <Text style={styles.error}>{errorMessage}</Text>}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    marginBottom: spacing(3),
  },
  label: {
    color: colors.textSecondary,
    marginBottom: spacing(1),
    fontSize: 14,
  },
  input: {
    backgroundColor: colors.surface,
    borderRadius: 999,
    paddingHorizontal: spacing(4),
    paddingVertical: spacing(2.5),
    color: colors.textPrimary,
    borderWidth: 1,
    borderColor: colors.border,
  },
  inputError: {
    borderColor: colors.error,
  },
  error: {
    color: colors.error,
    marginTop: spacing(1),
    fontSize: 12,
  },
});

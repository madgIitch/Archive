// src/components/ui/ScreenContainer.tsx
import React from 'react';
import { SafeAreaView, StyleSheet, View } from 'react-native';
import { colors, spacing } from '../../theme';

type Props = {
  children: React.ReactNode;
};

export const ScreenContainer: React.FC<Props> = ({ children }) => (
  <SafeAreaView style={styles.safe}>
    <View style={styles.container}>{children}</View>
  </SafeAreaView>
);

const styles = StyleSheet.create({
  safe: {
    flex: 1,
    backgroundColor: colors.background,
  },
  container: {
    flex: 1,
    paddingHorizontal: spacing(5),
    paddingTop: spacing(6),
  },
});

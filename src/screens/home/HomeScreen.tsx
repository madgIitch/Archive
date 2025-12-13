// src/screens/home/HomeScreen.tsx
import React from 'react';
import { Alert, StyleSheet, Text } from 'react-native';
import { ScreenContainer } from '../../components/ui/ScreenContainer';
import { Button } from '../../components/ui/Button';
import { colors, spacing } from '../../theme';
import { logout } from '../../services/auth';

export const HomeScreen: React.FC = () => {
  const handleLogout = async () => {
    try {
      await logout();
    } catch (error: any) {
      Alert.alert('Error al cerrar sesión', error.message ?? 'Inténtalo de nuevo.');
    }
  };

  return (
    <ScreenContainer>
      <Text style={styles.title}>ChronoDrop</Text>
      <Text style={styles.subtitle}>Tu línea de tiempo de mensajes al futuro.</Text>

      <Text style={styles.emptyState}>
        Aún no tienes cápsulas del tiempo.  
        Empieza creando tu primer mensaje al futuro.
      </Text>

      <Button label="Cerrar sesión" onPress={handleLogout} />
    </ScreenContainer>
  );
};

const styles = StyleSheet.create({
  title: {
    color: colors.textPrimary,
    fontSize: 28,
    fontWeight: '700',
    marginBottom: spacing(1),
  },
  subtitle: {
    color: colors.textSecondary,
    marginBottom: spacing(5),
  },
  emptyState: {
    color: colors.textSecondary,
    marginBottom: spacing(5),
  },
});

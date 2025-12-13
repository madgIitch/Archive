// src/screens/auth/LoginScreen.tsx
import React, { useState } from 'react';
import { Alert, StyleSheet, Text, TouchableOpacity } from 'react-native';
import { ScreenContainer } from '../../components/ui/ScreenContainer';
import { TextInput } from '../../components/ui/TextInput';
import { Button } from '../../components/ui/Button';
import { colors, spacing } from '../../theme';
import { loginWithEmail } from '../../services/auth';
import { isValidEmail, isValidPassword } from '../../utils/validators';
import { NativeStackScreenProps } from '@react-navigation/native-stack';

type AuthStackParamList = {
  Login: undefined;
  Register: undefined;
};

type Props = NativeStackScreenProps<AuthStackParamList, 'Login'>;

export const LoginScreen: React.FC<Props> = ({ navigation }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const [loading, setLoading] = useState(false);

  const handleLogin = async () => {
    if (!isValidEmail(email)) {
      Alert.alert('Email no válido', 'Revisa el formato del correo.');
      return;
    }
    if (!isValidPassword(password)) {
      Alert.alert('Contraseña muy corta', 'Mínimo 6 caracteres.');
      return;
    }

    try {
      setLoading(true);
      await loginWithEmail(email.trim(), password);
      // El listener global de auth te llevará al AppStack
    } catch (error: any) {
      Alert.alert('Error al iniciar sesión', error.message ?? 'Inténtalo de nuevo.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <ScreenContainer>
      <Text style={styles.title}>Bienvenido a ChronoDrop</Text>
      <Text style={styles.subtitle}>Envia mensajes a tu yo del futuro.</Text>

      <TextInput
        label="Email"
        value={email}
        onChangeText={setEmail}
        keyboardType="email-address"
        autoCapitalize="none"
        autoCorrect={false}
        placeholder="tucorreo@ejemplo.com"
      />

      <TextInput
        label="Contraseña"
        value={password}
        onChangeText={setPassword}
        secureTextEntry
        placeholder="••••••••"
      />

      <Button label="Iniciar sesión" onPress={handleLogin} loading={loading} />

      <TouchableOpacity onPress={() => navigation.navigate('Register')}>
        <Text style={styles.link}>
          ¿No tienes cuenta? <Text style={styles.linkBold}>Crear cuenta</Text>
        </Text>
      </TouchableOpacity>
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
    marginBottom: spacing(6),
  },
  link: {
    color: colors.textSecondary,
    marginTop: spacing(4),
    textAlign: 'center',
  },
  linkBold: {
    color: colors.accent,
    fontWeight: '600',
  },
});

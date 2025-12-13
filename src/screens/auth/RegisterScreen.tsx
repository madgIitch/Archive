// src/screens/auth/RegisterScreen.tsx
import React, { useState } from 'react';
import { Alert, StyleSheet, Text, TouchableOpacity } from 'react-native';
import { ScreenContainer } from '../../components/ui/ScreenContainer';
import { TextInput } from '../../components/ui/TextInput';
import { Button } from '../../components/ui/Button';
import { colors, spacing } from '../../theme';
import { registerWithEmail } from '../../services/auth';
import { isValidEmail, isValidPassword } from '../../utils/validators';
import { NativeStackScreenProps } from '@react-navigation/native-stack';

type AuthStackParamList = {
  Login: undefined;
  Register: undefined;
};

type Props = NativeStackScreenProps<AuthStackParamList, 'Register'>;

export const RegisterScreen: React.FC<Props> = ({ navigation }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [repeatPassword, setRepeatPassword] = useState('');

  const [loading, setLoading] = useState(false);

  const handleRegister = async () => {
    if (!isValidEmail(email)) {
      Alert.alert('Email no válido', 'Revisa el formato del correo.');
      return;
    }
    if (!isValidPassword(password)) {
      Alert.alert('Contraseña muy corta', 'Mínimo 6 caracteres.');
      return;
    }
    if (password !== repeatPassword) {
      Alert.alert('Contraseñas no coinciden', 'Asegúrate de que sean iguales.');
      return;
    }

    try {
      setLoading(true);
      await registerWithEmail(email.trim(), password);
      // Listener global redirige a AppStack
    } catch (error: any) {
      Alert.alert('Error al registrar', error.message ?? 'Inténtalo de nuevo.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <ScreenContainer>
      <Text style={styles.title}>Crea tu cuenta</Text>
      <Text style={styles.subtitle}>Tu yo del futuro te lo agradecerá.</Text>

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

      <TextInput
        label="Repetir contraseña"
        value={repeatPassword}
        onChangeText={setRepeatPassword}
        secureTextEntry
        placeholder="••••••••"
      />

      <Button label="Registrarse" onPress={handleRegister} loading={loading} />

      <TouchableOpacity onPress={() => navigation.navigate('Login')}>
        <Text style={styles.link}>
          ¿Ya tienes cuenta? <Text style={styles.linkBold}>Inicia sesión</Text>
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

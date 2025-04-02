import { View, Text, Button } from 'react-native';

export default function HomeScreen({ navigation }) {
    return (
        <View className="flex-1 items-center justify-center bg-white">
            <Text className="text-lg font-bold">¡Bienvenido a Home!</Text>
            <Button title="Ir a Detalles" onPress={() => navigation.navigate('Details')} />
        </View>
    );
}

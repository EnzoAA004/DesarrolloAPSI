import { View, Text, Button } from 'react-native';

export default function DetailsScreen({ navigation }) {
    return (
        <View className="flex-1 items-center justify-center bg-white">
            <Text className="text-lg font-bold">¡Esta es la pantalla de detalles!</Text>
            <Button title="Volver a Home" onPress={() => navigation.goBack()} />
        </View>
    );
}

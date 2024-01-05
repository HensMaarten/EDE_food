<template>
    <div>
        <GoogleLogin :callback="callback" />
    </div>
</template>
<script>
import { decodeCredential } from 'vue3-google-login'
import { mapActions } from 'vuex'
import { mapState } from 'vuex'

export default {
    name: 'LoginComponent',
    data() {
        return {
        }
    },
    methods: {
        callback(response) {
            try {
                let newUser = decodeCredential(response.credential);
                this.setLoggedIn(true)
                this.setUser(newUser)
                this.setToken(response.credential);
            } catch (error) {
                console.log(error)
            }

        },
        ...mapActions(['setLoggedIn', 'setUser', 'setToken'])
    },
    computed: {
        ...mapState(['loggedIn', 'user']),
    }
}
</script>
<style></style>
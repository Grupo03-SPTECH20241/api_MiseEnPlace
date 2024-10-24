package school.sptech.apimiseenplace.api.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import school.sptech.apimiseenplace.api.configuration.security.jwt.GerenciadorTokenJwt;
import school.sptech.apimiseenplace.service.AutenticacaoService;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguracao {
    private static final String ORIGENS_PERMITIDAS = "*";

    @Autowired
    private AutenticacaoService autenticacaoService;
    @Autowired
    private AutenticacaoEntryPoint autenticacaoEntryPoint;

    private static final AntPathRequestMatcher[] URLS_PERMITIDAS = {
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/swagger-ui.html"),
            new AntPathRequestMatcher("/swagger-resources"),
            new AntPathRequestMatcher("/swagger-resources/**"),
            new AntPathRequestMatcher("/configuration/ui"),
            new AntPathRequestMatcher("/configuration/security"),
            new AntPathRequestMatcher("/api/public/**"),
            new AntPathRequestMatcher("/api/public/authenticate"),
            new AntPathRequestMatcher("/webjars/**"),
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/actuator/*"),
            new AntPathRequestMatcher("/usuarios/login/**"),
            new AntPathRequestMatcher("/h2-console"),
            new AntPathRequestMatcher("/h2-console/**"),
            new AntPathRequestMatcher("/error/**"),
            new AntPathRequestMatcher("/usuarios"),
            new AntPathRequestMatcher("/usuarios/**"),
            new AntPathRequestMatcher("/pedidos"),
            new AntPathRequestMatcher("/pedidos/**"),
            new AntPathRequestMatcher("/pedidos-festa"),
            new AntPathRequestMatcher("/pedidos-festa/**"),
            new AntPathRequestMatcher("/produtos"),
            new AntPathRequestMatcher("/produtos/**"),
            new AntPathRequestMatcher("/recheios"),
            new AntPathRequestMatcher("/recheios/**"),
            new AntPathRequestMatcher("/personalizacoes"),
            new AntPathRequestMatcher("/personalizacoes/**"),
            new AntPathRequestMatcher("/clientes"),
            new AntPathRequestMatcher("/clientes/**"),
            new AntPathRequestMatcher("/enderecos"),
            new AntPathRequestMatcher("/enderecos/**"),
            new AntPathRequestMatcher("/forma-entregas"),
            new AntPathRequestMatcher("/forma-entregas/**"),
            new AntPathRequestMatcher("/festas"),
            new AntPathRequestMatcher("/festas/**"),
            new AntPathRequestMatcher("/pedidos"),
            new AntPathRequestMatcher("/pedidos/**"),
            new AntPathRequestMatcher("/produto-pedidos"),
            new AntPathRequestMatcher("/produto-pedidos/**"),
            new AntPathRequestMatcher("/metas"),
            new AntPathRequestMatcher("/metas/**"),
            new AntPathRequestMatcher("/coberturas"),
            new AntPathRequestMatcher("/coberturas/**"),
            new AntPathRequestMatcher("/massas"),
            new AntPathRequestMatcher("/massas/**"),
            new AntPathRequestMatcher("/forma-pagamento"),
            new AntPathRequestMatcher("/forma-pagamento/**"),
            new AntPathRequestMatcher("/unidades-medida"),
            new AntPathRequestMatcher("/unidades-medida/**"),
            new AntPathRequestMatcher("/pilha"),
            new AntPathRequestMatcher("/pilha/**"),
            new AntPathRequestMatcher("/fila"),
            new AntPathRequestMatcher("/fila/**"),
            new AntPathRequestMatcher("/tipo-produtos"),
            new AntPathRequestMatcher("/tipo-produtos/**"),
            new AntPathRequestMatcher("localhost:3000/**"),
            new AntPathRequestMatcher("/quantidade-vendidos-mes"),
            new AntPathRequestMatcher("/matriz"),
            new AntPathRequestMatcher("/matriz/**"),
            new AntPathRequestMatcher("/quantidade-vendidos-dia"),
            new AntPathRequestMatcher("/quantidade-vendidos-semana"),
            new AntPathRequestMatcher("/quantidade-vendidos-tipo-produto"),
            new AntPathRequestMatcher("/quantidade-vendida-valor-vendido"),
            new AntPathRequestMatcher("/quantidade-vendida-valor-vendido-semana"),
            new AntPathRequestMatcher("/quantidade-vendida-valor-vendido-dia"),
            new AntPathRequestMatcher("/hashtable"),
            new AntPathRequestMatcher("/hashtable/**"),
            new AntPathRequestMatcher("/txt"),
            new AntPathRequestMatcher("/txt/**"),
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(CsrfConfigurer<HttpSecurity>::disable)
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(URLS_PERMITIDAS)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .exceptionHandling(handling -> handling.authenticationEntryPoint(autenticacaoEntryPoint))
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(new AutenticacaoProvider(autenticacaoService, passwordEncoder()));
        return authenticationManagerBuilder.build();
    }

    @Bean
    public AutenticacaoEntryPoint jwtAuthenticationEntryPointBean(){
        return new AutenticacaoEntryPoint();
    }

    @Bean
    public AutenticacaoFilter jwtAuthenticationFilterBean(){
        return new AutenticacaoFilter(autenticacaoService, jwtAuthenticationUtilBean());
    }

    @Bean
    public GerenciadorTokenJwt jwtAuthenticationUtilBean(){
        return new GerenciadorTokenJwt();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuracao = new CorsConfiguration();
        configuracao.applyPermitDefaultValues();
        configuracao.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name(),
                HttpMethod.HEAD.name(),
                HttpMethod.TRACE.name()));

        configuracao.setExposedHeaders(List.of(HttpHeaders.CONTENT_DISPOSITION));

        UrlBasedCorsConfigurationSource origem = new UrlBasedCorsConfigurationSource();
        origem.registerCorsConfiguration("/**", configuracao);

        return origem;
    }

}
